import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext.jsx';
import GuestButton from '../components/login/GuestButton.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';

export default function LoginPage() {
  const { ensureGuestSession } = useSession();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleGuestLogin = async () => {
    setLoading(true);
    setError('');
    try {
      await ensureGuestSession();
      navigate('/dulceria');
    } catch (err) {
      setError('No se pudo iniciar la sesión de invitado. Intenta nuevamente.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page" style={{ textAlign: 'center', paddingTop: 96 }}>
      <h1>Ingresa a tu cuenta</h1>
      <p style={{ color: 'var(--text-muted)', marginBottom: 32 }}>
        Continúa como invitado para elegir tu dulcería y completar la compra.
      </p>
      <ErrorBanner message={error} />
      <GuestButton onClick={handleGuestLogin} loading={loading} />
    </div>
  );
}
