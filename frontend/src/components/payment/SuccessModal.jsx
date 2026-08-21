import Modal from '../common/Modal.jsx';
import { CheckCircleIcon } from '../common/icons.jsx';

export default function SuccessModal({ onClose }) {
  return (
    <Modal onClose={onClose}>
      <div className="success-modal-icon">
        <CheckCircleIcon width={36} height={36} />
      </div>
      <h2 className="success-modal-title">¡Compra exitosa!</h2>
      <p className="success-modal-text">
        Tu pago fue procesado correctamente. Disfruta tu función.
      </p>
      <button className="btn-primary" onClick={onClose} style={{ width: '100%' }}>
        Volver al Home
      </button>
    </Modal>
  );
}
