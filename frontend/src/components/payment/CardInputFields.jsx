export default function CardInputFields({ form, errors, onChange }) {
  return (
    <>
      <div className="field">
        <label>Número de tarjeta</label>
        <input
          type="text"
          inputMode="numeric"
          maxLength={16}
          placeholder="4097440000000004"
          value={form.cardNumber}
          onChange={(e) => onChange('cardNumber', e.target.value.replace(/\D/g, ''))}
        />
        {errors.cardNumber && <span className="field-error">{errors.cardNumber}</span>}
      </div>

      <div style={{ display: 'flex', gap: 16 }}>
        <div className="field" style={{ flex: 1 }}>
          <label>Fecha de expiración (MM/AA)</label>
          <input
            type="text"
            placeholder="12/30"
            maxLength={5}
            value={form.expirationDate}
            onChange={(e) => onChange('expirationDate', e.target.value)}
          />
          {errors.expirationDate && <span className="field-error">{errors.expirationDate}</span>}
        </div>
        <div className="field" style={{ width: 100 }}>
          <label>CVV</label>
          <input
            type="text"
            inputMode="numeric"
            maxLength={4}
            value={form.cvv}
            onChange={(e) => onChange('cvv', e.target.value.replace(/\D/g, ''))}
          />
          {errors.cvv && <span className="field-error">{errors.cvv}</span>}
        </div>
      </div>

      <div className="field">
        <label>Nombre en la tarjeta</label>
        <input
          type="text"
          placeholder="APPROVED"
          value={form.cardHolderName}
          onChange={(e) => onChange('cardHolderName', e.target.value)}
        />
        {errors.cardHolderName && <span className="field-error">{errors.cardHolderName}</span>}
      </div>
    </>
  );
}
