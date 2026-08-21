import { ChevronRightIcon } from '../common/icons.jsx';
import { useCart } from '../../context/CartContext.jsx';

export default function PremiereCard({ premiere, onSelect }) {
  const { ticketItems } = useCart();
  const selectedTicket = ticketItems.find((item) => item.premiereId === premiere.id);

  return (
    <article className="premiere-card" onClick={() => onSelect(premiere)}>
      <div className="premiere-card-image">
        <img src={premiere.imageUrl} alt={premiere.title} loading="lazy" />
        {selectedTicket && (
          <span className="badge badge-accent premiere-card-badge">
            {selectedTicket.quantity} {selectedTicket.quantity === 1 ? 'entrada' : 'entradas'}
          </span>
        )}
      </div>
      <div className="premiere-card-body">
        <h3>{premiere.title}</h3>
        <p>{premiere.description}</p>
        <span className="premiere-card-link">
          {selectedTicket ? 'Editar entradas' : 'Comprar entradas'}
          <ChevronRightIcon width={16} height={16} />
        </span>
      </div>
    </article>
  );
}
