export default function GuestButton({ onClick, loading }) {
  return (
    <button className="btn-primary" onClick={onClick} disabled={loading}>
      {loading ? 'Ingresando...' : 'Continuar como Invitado'}
    </button>
  );
}
