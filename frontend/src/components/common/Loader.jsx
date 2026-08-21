export default function Loader({ label = 'Cargando...' }) {
  return (
    <div className="loader">
      <span className="spinner" />
      <span>{label}</span>
    </div>
  );
}
