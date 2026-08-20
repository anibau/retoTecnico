import TopMenu from './TopMenu.jsx';

export default function Layout({ children }) {
  return (
    <div>
      <TopMenu />
      <main>{children}</main>
    </div>
  );
}
