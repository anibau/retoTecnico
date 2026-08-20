import ProductCard from './ProductCard.jsx';

export default function ProductGrid({ products }) {
  if (!products.length) {
    return <p style={{ color: 'var(--text-muted)' }}>No hay productos disponibles.</p>;
  }
  return (
    <div
      style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fill, minmax(220px, 1fr))',
        gap: 16,
      }}
    >
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}
