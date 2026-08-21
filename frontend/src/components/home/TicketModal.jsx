import { useState } from 'react';
import Modal from '../common/Modal.jsx';
import { TicketIcon } from '../common/icons.jsx';
import { formatCurrency } from '../../utils/formatCurrency.js';

export const TICKET_PRICE = 5;

export default function TicketModal({ premiere, initialQuantity = 1, onConfirm, onClose }) {
  const [quantity, setQuantity] = useState(initialQuantity);

  const decrease = () => setQuantity((q) => Math.max(1, q - 1));
  const increase = () => setQuantity((q) => Math.min(10, q + 1));

  return (
    <Modal onClose={onClose}>
      <div className="ticket-modal-icon">
        <TicketIcon width={30} height={30} />
      </div>
      <h2 className="ticket-modal-title">{premiere.title}</h2>
      <p className="ticket-modal-subtitle">¿Cuántas entradas deseas comprar?</p>

      <div className="ticket-modal-price">{formatCurrency(TICKET_PRICE)} por entrada</div>

      <div className="ticket-modal-stepper">
        <button type="button" className="btn-step" onClick={decrease} disabled={quantity <= 1}>
          −
        </button>
        <span>{quantity}</span>
        <button type="button" className="btn-step" onClick={increase} disabled={quantity >= 10}>
          +
        </button>
      </div>

      <div className="ticket-modal-total">
        <span>Total entradas</span>
        <strong>{formatCurrency(quantity * TICKET_PRICE)}</strong>
      </div>

      <button
        type="button"
        className="btn-primary ticket-modal-cta"
        onClick={() => onConfirm(premiere, quantity)}
      >
        Continuar
      </button>
      <button type="button" className="btn-ghost ticket-modal-cancel" onClick={onClose}>
        Cancelar
      </button>
    </Modal>
  );
}
