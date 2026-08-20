import { NavLink } from 'react-router-dom';

const linkStyle = ({ isActive }) => ({
  padding: '10px 16px',
  borderRadius: 8,
  fontWeight: 600,
  color: isActive ? '#fff' : 'var(--text-muted)',
  background: isActive ? 'var(--accent)' : 'transparent',
});

export default function TopMenu() {
  return (
    <header
      style={{
        borderBottom: '1px solid var(--border)',
        background: 'var(--bg-elevated)',
        position: 'sticky',
        top: 0,
        zIndex: 10,
      }}
    >
      <nav
        style={{
          maxWidth: 1100,
          margin: '0 auto',
          display: 'flex',
          alignItems: 'center',
          gap: 8,
          padding: '14px 20px',
        }}
      >
        <strong style={{ marginRight: 'auto', fontSize: '1.1rem' }}>🎬 Cines Ecommerce</strong>
        <NavLink to="/" style={linkStyle} end>
          Home
        </NavLink>
        <NavLink to="/dulceria" style={linkStyle}>
          Dulcería
        </NavLink>
        <NavLink to="/login" style={linkStyle}>
          Login
        </NavLink>
      </nav>
    </header>
  );
}
