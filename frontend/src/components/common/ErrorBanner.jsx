export default function ErrorBanner({ message }) {
  if (!message) {
    return null;
  }
  return (
    <div
      style={{
        background: 'rgba(224, 57, 62, 0.12)',
        border: '1px solid var(--danger)',
        color: '#ff9a9d',
        borderRadius: 8,
        padding: '12px 16px',
        marginBottom: 16,
      }}
    >
      {message}
    </div>
  );
}
