import { useEffect, useMemo, useState } from 'react';
import { fetchCandystoreProducts } from '../api/candystoreApi.js';
import ProductGrid from '../components/candystore/ProductGrid.jsx';
import CartSummary from '../components/candystore/CartSummary.jsx';
import Loader from '../components/common/Loader.jsx';
import ErrorBanner from '../components/common/ErrorBanner.jsx';
import { SearchIcon } from '../components/common/icons.jsx';

export default function CandystorePage() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('Todos');

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

  const categories = useMemo(() => {
    const unique = Array.from(new Set(products.map((p) => p.category).filter(Boolean)));
    return ['Todos', ...unique];
  }, [products]);

  const filteredProducts = useMemo(() => {
    const term = search.trim().toLowerCase();
    return products.filter((product) => {
      const matchesCategory = category === 'Todos' || product.category === category;
      const matchesSearch = !term || product.name.toLowerCase().includes(term);
      return matchesCategory && matchesSearch;
    });
  }, [products, search, category]);

  return (
    <div className="page">
      <div className="page-header">
        <h1>Dulcería</h1>
      </div>
      <ErrorBanner message={error} />

      {loading ? (
        <Loader label="Cargando productos..." />
      ) : (
        <div className="candystore-layout">
          <div className="candystore-main">
            <div className="search-input candystore-search">
              <SearchIcon width={17} height={17} />
              <input
                type="text"
                placeholder="Buscar producto..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <div className="candystore-tabs">
              {categories.map((cat) => (
                <button
                  key={cat}
                  type="button"
                  className={`chip${cat === category ? ' active' : ''}`}
                  onClick={() => setCategory(cat)}
                >
                  {cat}
                </button>
              ))}
            </div>

            <ProductGrid products={filteredProducts} />
          </div>

          <CartSummary />
        </div>
      )}
    </div>
  );
}
