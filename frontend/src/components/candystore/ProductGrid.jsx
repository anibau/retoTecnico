import ProductCard from './ProductCard.jsx';

export default function ProductGrid({ products }) {
  if (!products.length) {
    return <p style={{ color: 'var(--text-muted)' }}>No hay productos disponibles.</p>;
  }
  return (
    <div className="product-grid">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
}
