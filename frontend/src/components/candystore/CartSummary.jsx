import { useNavigate } from 'react-router-dom';
import { useCart } from '../../context/CartContext.jsx';
import { formatCurrency } from '../../utils/formatCurrency.js';
import { TrashIcon } from '../common/icons.jsx';

export default function CartSummary() {
  const { items, total, updateQuantity, clear } = useCart();
  const navigate = useNavigate();
  const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <aside className="cart-panel card">
      <div className="cart-panel-header">
        <h3>Tu carrito ({itemCount})</h3>
        {items.length > 0 && (
          <button
            type="button"
            className="btn-icon"
            onClick={clear}
            aria-label="Vaciar carrito"
            title="Vaciar carrito"
          >
            <TrashIcon width={16} height={16} />
          </button>
        )}
      </div>

      {items.length === 0 ? (
        <p className="cart-panel-empty">Aún no has agregado productos.</p>
      ) : (
        <div className="cart-panel-items">
          {items.map((item) => (
            <div key={item.productId} className="cart-panel-item">
              <div className="cart-panel-item-info">
                <span className="cart-panel-item-name">{item.name}</span>
                <span className="cart-panel-item-price">
                  {formatCurrency(item.unitPrice)} c/u
                </span>
              </div>
              <div className="cart-panel-item-controls">
                <button
                  type="button"
                  className="btn-step"
                  onClick={() => updateQuantity(item.productId, item.quantity - 1)}
                >
                  −
                </button>
                <span>{item.quantity}</span>
                <button
                  type="button"
                  className="btn-step"
                  onClick={() => updateQuantity(item.productId, item.quantity + 1)}
                >
                  +
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="cart-panel-totals">
        <div className="cart-panel-row">
          <span>Subtotal</span>
          <span>{formatCurrency(total)}</span>
        </div>
        <div className="cart-panel-row">
          <span>Envío</span>
          <span>{formatCurrency(0)}</span>
        </div>
        <div className="cart-panel-row cart-panel-total">
          <span>Total</span>
          <span>{formatCurrency(total)}</span>
        </div>
      </div>

      <button
        className="btn-primary cart-panel-cta"
        disabled={itemCount === 0}
        onClick={() => navigate('/pago')}
      >
        Continuar
      </button>
    </aside>
  );
}
