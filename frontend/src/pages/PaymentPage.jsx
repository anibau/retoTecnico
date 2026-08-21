import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext.jsx';
import { completeOrder, submitPayment } from '../api/completeApi.js';
import CheckoutStepper from '../components/payment/CheckoutStepper.jsx';
import PaymentForm from '../components/payment/PaymentForm.jsx';
import OrderSummary from '../components/payment/OrderSummary.jsx';
import SuccessModal from '../components/payment/SuccessModal.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';

export default function PaymentPage() {
  const { items, total, clear } = useCart();
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (form) => {
    setSubmitting(true);
    setError('');
    try {
      const paymentResponse = await submitPayment({
        cardNumber: form.cardNumber,
        expirationDate: form.expirationDate,
        cvv: form.cvv,
        cardHolderName: form.cardHolderName,
        email: form.email,
        fullName: form.fullName,
        documentType: form.documentType,
        documentNumber: form.documentNumber,
        amount: total,
        currency: 'PEN',
        items: items.map((item) => ({
          productId: item.kind === 'ticket' ? -item.premiereId : item.productId,
          productName: item.name,
          quantity: item.quantity,
          unitPrice: item.unitPrice,
        })),
      });

      if (!paymentResponse.approved) {
        setError(paymentResponse.responseMessage || 'El pago fue rechazado por la pasarela.');
        return;
      }

      const completeResponse = await completeOrder({
        email: form.email,
        fullName: form.fullName,
        documentNumber: form.documentNumber,
        operationDate: paymentResponse.operationDate,
        transactionId: paymentResponse.transactionId,
      });

      if (completeResponse.responseCode === '0') {
        setSuccess(true);
        clear();
      } else {
        setError(completeResponse.message || 'No se pudo finalizar la transacción.');
      }
    } catch (err) {
      setError('Ocurrió un error al procesar el pago. Intenta nuevamente.');
    } finally {
      setSubmitting(false);
    }
  };

  if (items.length === 0 && !success) {
    return (
      <div className="page">
        <h1>Pago</h1>
        <p style={{ color: 'var(--text-muted)' }}>
          Tu carrito está vacío. Vuelve a Dulcería para agregar productos.
        </p>
      </div>
    );
  }

  return (
    <div className="page">
      <CheckoutStepper currentStep={success ? 4 : 3} />
      <ErrorBanner message={error} />

      <div className="payment-layout">
        <PaymentForm onSubmit={handleSubmit} />
        <OrderSummary items={items} total={total} submitting={submitting} />
      </div>

      {success && <SuccessModal onClose={() => navigate('/')} />}
    </div>
  );
}
