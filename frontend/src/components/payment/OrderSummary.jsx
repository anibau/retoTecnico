import { formatCurrency } from '../../utils/formatCurrency.js';
import { LockIcon } from '../common/icons.jsx';
import { PAYMENT_FORM_ID } from './PaymentForm.jsx';

export default function OrderSummary({ items, total, submitting }) {
  return (
    <aside className="order-summary card">
      <h3>Resumen de compra</h3>

      <div className="order-summary-items">
        {items.map((item) => (
          <div key={item.productId} className="order-summary-row">
            <span>
              {item.name} <span className="order-summary-qty">x{item.quantity}</span>
            </span>
            <span>{formatCurrency(item.unitPrice * item.quantity)}</span>
          </div>
        ))}
      </div>

      <div className="order-summary-totals">
        <div className="order-summary-row muted">
          <span>Subtotal</span>
          <span>{formatCurrency(total)}</span>
        </div>
        <div className="order-summary-row muted">
          <span>Envío</span>
          <span>{formatCurrency(0)}</span>
        </div>
        <div className="order-summary-row order-summary-total">
          <span>Total a pagar</span>
          <span>{formatCurrency(total)}</span>
        </div>
      </div>

      <button
        type="submit"
        form={PAYMENT_FORM_ID}
        className="btn-primary order-summary-cta"
        disabled={submitting}
      >
        <LockIcon width={16} height={16} />
        {submitting ? 'Procesando pago...' : 'Pagar ahora'}
      </button>

      <p className="order-summary-terms">Al pagar, aceptas nuestros Términos y Condiciones.</p>
    </aside>
  );
}
