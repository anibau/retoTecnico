export default function Modal({ children, onClose }) {
  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="card modal-panel" onClick={(e) => e.stopPropagation()}>
        {children}
      </div>
    </div>
  );
}
