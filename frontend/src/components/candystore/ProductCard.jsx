import { useCart } from '../../context/CartContext.jsx';
import { formatCurrency } from '../../utils/formatCurrency.js';
import { categoryIcon } from '../../utils/categoryIcon.js';

export default function ProductCard({ product }) {
  const { items, addItem, updateQuantity } = useCart();
  const cartItem = items.find((item) => item.productId === product.id);
  const quantity = cartItem ? cartItem.quantity : 0;

  return (
    <div className="product-card">
      <div className="product-card-icon">{categoryIcon(product.category)}</div>
      <h4>{product.name}</h4>
      <p>{product.description}</p>
      <strong>{formatCurrency(product.price)}</strong>

      {quantity === 0 ? (
        <button className="btn-primary product-card-add" onClick={() => addItem(product)}>
          Agregar
        </button>
      ) : (
        <div className="product-card-stepper">
          <button
            type="button"
            className="btn-step"
            onClick={() => updateQuantity(product.id, quantity - 1)}
          >
            −
          </button>
          <span>{quantity}</span>
          <button type="button" className="btn-step" onClick={() => addItem(product)}>
            +
          </button>
        </div>
      )}
    </div>
  );
}
