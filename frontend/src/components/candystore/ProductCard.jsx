import { useCart } from '../../context/CartContext.jsx';
import { formatCurrency } from '../../utils/formatCurrency.js';

export default function ProductCard({ product }) {
  const { items, addItem, updateQuantity } = useCart();
  const cartItem = items.find((item) => item.productId === product.id);
  const quantity = cartItem ? cartItem.quantity : 0;

  return (
    <div className="card" style={{ padding: 16, display: 'flex', flexDirection: 'column', gap: 8 }}>
      <h4 style={{ margin: 0 }}>{product.name}</h4>
      <p style={{ margin: 0, color: 'var(--text-muted)', flexGrow: 1 }}>{product.description}</p>
      <strong>{formatCurrency(product.price)}</strong>
      <div style={{ display: 'flex', alignItems: 'center', gap: 12, marginTop: 8 }}>
        <button
          className="btn-secondary"
          style={{ padding: '4px 12px' }}
          onClick={() => updateQuantity(product.id, quantity - 1)}
          disabled={quantity === 0}
        >
          −
        </button>
        <span>{quantity}</span>
        <button
          className="btn-secondary"
          style={{ padding: '4px 12px' }}
          onClick={() => addItem(product)}
        >
          +
        </button>
      </div>
    </div>
  );
}
