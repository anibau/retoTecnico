import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSession } from '../context/SessionContext.jsx';
import GuestButton from '../components/login/GuestButton.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';
import { ShieldIcon, ZapIcon, CheckCircleIcon } from '../components/common/icons.jsx';

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
    <div className="login-layout">
      <div className="login-art" aria-hidden="true">
        <div className="login-art-glow" />
        <div className="login-art-icons">
          <span>🎬</span>
          <span>🍿</span>
          <span>🥤</span>
        </div>
      </div>

      <div className="login-panel">
        <div className="login-panel-inner">
          <h1>Bienvenido</h1>
          <p>Inicia sesión para continuar</p>

          <ErrorBanner message={error} />

          <GuestButton onClick={handleGuestLogin} loading={loading} />

          <p className="login-terms">
            Al continuar, aceptas nuestros Términos y Condiciones y Política de Privacidad.
          </p>

          <div className="login-trust">
            <span>
              <ShieldIcon width={15} height={15} /> Seguro
            </span>
            <span>
              <ZapIcon width={15} height={15} /> Rápido
            </span>
            <span>
              <CheckCircleIcon width={15} height={15} /> Sin complicaciones
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
