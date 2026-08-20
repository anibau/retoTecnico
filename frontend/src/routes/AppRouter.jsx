import { Route, Routes } from 'react-router-dom';
import HomePage from '../pages/HomePage.jsx';
import LoginPage from '../pages/LoginPage.jsx';
import CandystorePage from '../pages/CandystorePage.jsx';
import PaymentPage from '../pages/PaymentPage.jsx';

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/dulceria" element={<CandystorePage />} />
      <Route path="/pago" element={<PaymentPage />} />
    </Routes>
  );
}
