import { UserIcon } from '../common/icons.jsx';

export default function GuestButton({ onClick, loading }) {
  return (
    <button className="btn-primary login-guest-btn" onClick={onClick} disabled={loading}>
      <UserIcon width={17} height={17} />
      {loading ? 'Ingresando...' : 'Continuar como invitado'}
    </button>
  );
}
