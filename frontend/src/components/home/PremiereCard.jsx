export default function PremiereCard({ premiere, onSelect }) {
  return (
    <article
      className="card"
      style={{
        display: 'flex',
        gap: 20,
        padding: 16,
        marginBottom: 16,
        alignItems: 'center',
      }}
    >
      <img
        src={premiere.imageUrl}
        alt={premiere.title}
        onClick={() => onSelect(premiere)}
        style={{
          width: 180,
          height: 120,
          objectFit: 'cover',
          borderRadius: 8,
          cursor: 'pointer',
          flexShrink: 0,
        }}
      />
      <div>
        <h3 style={{ margin: '0 0 8px' }}>{premiere.title}</h3>
        <p style={{ margin: 0, color: 'var(--text-muted)' }}>{premiere.description}</p>
      </div>
    </article>
  );
}
