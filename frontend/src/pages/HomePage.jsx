import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchPremieres } from '../api/premieresApi.js';
import { useSession } from '../context/SessionContext.jsx';
import PremiereList from '../components/home/PremiereList.jsx';
import Loader from '../components/common/Loader.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';

export default function HomePage() {
  const { ensureGuestSession } = useSession();
  const [premieres, setPremieres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    let cancelled = false;

    async function load() {
      setLoading(true);
      setError('');
      try {
        await ensureGuestSession();
        const data = await fetchPremieres();
        if (!cancelled) {
          setPremieres(data);
        }
      } catch (err) {
        if (!cancelled) {
          setError('No se pudo cargar la cartelera. Intenta nuevamente más tarde.');
        }
      } finally {
        if (!cancelled) {
          setLoading(false);
        }
      }
    }

    load();
    return () => {
      cancelled = true;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="page">
      <h1>Estrenos</h1>
      <ErrorBanner message={error} />
      {loading ? <Loader label="Cargando cartelera..." /> : (
        <PremiereList premieres={premieres} onSelect={() => navigate('/login')} />
      )}
    </div>
  );
}
