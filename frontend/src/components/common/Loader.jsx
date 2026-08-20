export default function Loader({ label = 'Cargando...' }) {
  return (
    <div style={{ padding: 40, textAlign: 'center', color: 'var(--text-muted)' }}>{label}</div>
  );
}
