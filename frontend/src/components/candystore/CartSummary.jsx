import { useNavigate } from 'react-router-dom';
import { useCart } from '../../context/CartContext.jsx';
import { formatCurrency } from '../../utils/formatCurrency.js';

export default function CartSummary() {
  const { items, total } = useCart();
  const navigate = useNavigate();
  const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <div
      className="card"
      style={{
        position: 'sticky',
        bottom: 20,
        marginTop: 24,
        padding: '16px 20px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
      }}
    >
      <div>
        <div style={{ color: 'var(--text-muted)', fontSize: '0.85rem' }}>{itemCount} producto(s)</div>
        <strong style={{ fontSize: '1.2rem' }}>Total: {formatCurrency(total)}</strong>
      </div>
      <button className="btn-primary" disabled={itemCount === 0} onClick={() => navigate('/pago')}>
        Continuar
      </button>
    </div>
  );
}
