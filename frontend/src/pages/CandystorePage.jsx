import { useEffect, useState } from 'react';
import { fetchCandystoreProducts } from '../api/candystoreApi.js';
import ProductGrid from '../components/candystore/ProductGrid.jsx';
import CartSummary from '../components/candystore/CartSummary.jsx';
import Loader from '../components/common/Loader.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';

export default function CandystorePage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let cancelled = false;

    async function load() {
      setLoading(true);
      setError('');
      try {
        const data = await fetchCandystoreProducts();
        if (!cancelled) {
          setProducts(data);
        }
      } catch (err) {
        if (!cancelled) {
          setError('No se pudo cargar la dulcería. Intenta nuevamente más tarde.');
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
  }, []);

  return (
    <div className="page">
      <h1>Dulcería</h1>
      <ErrorBanner message={error} />
      {loading ? <Loader label="Cargando productos..." /> : <ProductGrid products={products} />}
      <CartSummary />
    </div>
  );
}
