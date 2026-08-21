import { NavLink, useNavigate } from 'react-router-dom';
import { useCart } from '../../context/CartContext.jsx';
import { TicketIcon, CartIcon } from '../common/icons.jsx';

const links = [
  { to: '/', label: 'Home', end: true },
  { to: '/dulceria', label: 'Dulcería' },
  { to: '/login', label: 'Login' },
];

export default function TopMenu() {
  const { items } = useCart();
  const navigate = useNavigate();
  const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <header className="topmenu">
      <nav className="topmenu-inner">
        <div className="topmenu-brand">
          <TicketIcon width={22} height={22} />
          <span>Cines Ecommerce</span>
        </div>

        <div className="topmenu-links">
          {links.map((link) => (
            <NavLink
              key={link.to}
              to={link.to}
              end={link.end}
              className={({ isActive }) => `topmenu-link${isActive ? ' active' : ''}`}
            >
              {link.label}
            </NavLink>
          ))}
        </div>

        <button
          type="button"
          className="btn-icon topmenu-cart"
          onClick={() => navigate('/dulceria')}
          aria-label="Ver carrito"
        >
          <CartIcon width={19} height={19} />
          {itemCount > 0 && <span className="topmenu-cart-badge">{itemCount}</span>}
        </button>
      </nav>
    </header>
  );
}
