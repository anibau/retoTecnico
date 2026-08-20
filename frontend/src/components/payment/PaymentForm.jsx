import { useState } from 'react';
import CardInputFields from './CardInputFields.jsx';
import { isValidCvv, isValidEmail, isValidExpiration, isValidLuhn } from '../../utils/validators.js';

const DOCUMENT_TYPES = ['DNI', 'CE', 'Pasaporte'];

const initialForm = {
  cardNumber: '',
  expirationDate: '',
  cvv: '',
  cardHolderName: '',
  email: '',
  fullName: '',
  documentType: 'DNI',
  documentNumber: '',
};

function validate(form) {
  const errors = {};
  if (!isValidLuhn(form.cardNumber)) {
    errors.cardNumber = 'Número de tarjeta inválido (16 dígitos).';
  }
  if (!isValidExpiration(form.expirationDate)) {
    errors.expirationDate = 'Formato MM/AA, no vencida.';
  }
  if (!isValidCvv(form.cvv)) {
    errors.cvv = 'CVV inválido.';
  }
  if (!form.cardHolderName.trim()) {
    errors.cardHolderName = 'Requerido.';
  }
  if (!isValidEmail(form.email)) {
    errors.email = 'Correo inválido.';
  }
  if (!form.fullName.trim()) {
    errors.fullName = 'Requerido.';
  }
  if (!form.documentNumber.trim()) {
    errors.documentNumber = 'Requerido.';
  }
  return errors;
}

export default function PaymentForm({ onSubmit, submitting }) {
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate(form);
    setErrors(validationErrors);
    if (Object.keys(validationErrors).length === 0) {
      onSubmit(form);
    }
  };

  return (
    <form className="card" style={{ padding: 24 }} onSubmit={handleSubmit}>
      <CardInputFields form={form} errors={errors} onChange={handleChange} />

      <div className="field">
        <label>Correo electrónico</label>
        <input
          type="email"
          value={form.email}
          onChange={(e) => handleChange('email', e.target.value)}
        />
        {errors.email && <span className="field-error">{errors.email}</span>}
      </div>

      <div className="field">
        <label>Nombre completo</label>
        <input
          type="text"
          value={form.fullName}
          onChange={(e) => handleChange('fullName', e.target.value)}
        />
        {errors.fullName && <span className="field-error">{errors.fullName}</span>}
      </div>

      <div style={{ display: 'flex', gap: 16 }}>
        <div className="field" style={{ width: 140 }}>
          <label>Tipo de documento</label>
          <select
            value={form.documentType}
            onChange={(e) => handleChange('documentType', e.target.value)}
          >
            {DOCUMENT_TYPES.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        <div className="field" style={{ flex: 1 }}>
          <label>Número de documento</label>
          <input
            type="text"
            value={form.documentNumber}
            onChange={(e) => handleChange('documentNumber', e.target.value)}
          />
          {errors.documentNumber && <span className="field-error">{errors.documentNumber}</span>}
        </div>
      </div>

      <button className="btn-primary" type="submit" disabled={submitting} style={{ width: '100%', marginTop: 8 }}>
        {submitting ? 'Procesando pago...' : 'Pagar'}
      </button>
    </form>
  );
}
