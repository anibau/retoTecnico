import Modal from '../common/Modal.jsx';

export default function SuccessModal({ onClose }) {
  return (
    <Modal onClose={onClose}>
      <div style={{ fontSize: 48, marginBottom: 12 }}>✅</div>
      <h2 style={{ margin: '0 0 8px' }}>¡Compra exitosa!</h2>
      <p style={{ color: 'var(--text-muted)', marginBottom: 24 }}>
        Tu pago fue procesado correctamente. Disfruta tu función.
      </p>
      <button className="btn-primary" onClick={onClose}>
        Volver al Home
      </button>
    </Modal>
  );
}
