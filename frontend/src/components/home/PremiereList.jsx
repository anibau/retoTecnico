import PremiereCard from './PremiereCard.jsx';

export default function PremiereList({ premieres, onSelect }) {
  if (!premieres.length) {
    return <p style={{ color: 'var(--text-muted)' }}>No hay estrenos disponibles.</p>;
  }
  return (
    <div className="premiere-grid">
      {premieres.map((premiere) => (
        <PremiereCard key={premiere.id} premiere={premiere} onSelect={onSelect} />
      ))}
    </div>
  );
}
