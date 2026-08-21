import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchPremieres } from '../api/premieresApi.js';
import { useSession } from '../context/SessionContext.jsx';
import { useCart } from '../context/CartContext.jsx';
import PremiereList from '../components/home/PremiereList.jsx';
import TicketModal, { TICKET_PRICE } from '../components/home/TicketModal.jsx';
import Loader from '../components/common/Loader.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';
import { PlayIcon } from '../components/common/icons.jsx';

export default function HomePage() {
  const { ensureGuestSession } = useSession();
  const { setTicketQuantity, ticketItems } = useCart();
  const [premieres, setPremieres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedPremiere, setSelectedPremiere] = useState(null);
  const navigate = useNavigate();
  const listRef = useRef(null);

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

  const handleConfirmTickets = (premiere, quantity) => {
    setTicketQuantity(premiere, quantity, TICKET_PRICE);
    setSelectedPremiere(null);
    navigate('/login');
  };

  return (
    <div>
      <section className="hero">
        <div className="hero-copy">
          <h1>
            Vive historias
            <br />
            inolvidables
          </h1>
          <p>Los mejores estrenos te esperan. Elige, disfruta y vive la experiencia.</p>
          <button
            type="button"
            className="btn-primary hero-cta"
            onClick={() => listRef.current?.scrollIntoView({ behavior: 'smooth' })}
          >
            Ver estrenos
            <PlayIcon width={16} height={16} />
          </button>
        </div>
        <div className="hero-art" aria-hidden="true">
          <div className="hero-art-glow" />
          <span className="hero-art-figure">🎬</span>
        </div>
      </section>

      <div className="page" ref={listRef} style={{ paddingTop: 8 }}>
        <div className="page-header">
          <h2 className="section-title">Estrenos destacados</h2>
        </div>
        <ErrorBanner message={error} />
        {loading ? (
          <Loader label="Cargando cartelera..." />
        ) : (
          <PremiereList premieres={premieres} onSelect={setSelectedPremiere} />
        )}
      </div>

      {selectedPremiere && (
        <TicketModal
          premiere={selectedPremiere}
          initialQuantity={
            ticketItems.find((item) => item.premiereId === selectedPremiere.id)?.quantity || 1
          }
          onConfirm={handleConfirmTickets}
          onClose={() => setSelectedPremiere(null)}
        />
      )}
    </div>
  );
}
