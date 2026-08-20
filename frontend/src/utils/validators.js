export function isValidLuhn(cardNumber) {
  const digits = String(cardNumber).replace(/\D/g, '');
  if (digits.length !== 16) {
    return false;
  }
  let sum = 0;
  let shouldDouble = false;
  for (let i = digits.length - 1; i >= 0; i -= 1) {
    let digit = Number(digits[i]);
    if (shouldDouble) {
      digit *= 2;
      if (digit > 9) {
        digit -= 9;
      }
    }
    sum += digit;
    shouldDouble = !shouldDouble;
  }
  return sum % 10 === 0;
}

export function isValidExpiration(mmYY) {
  const match = /^(\d{2})\/(\d{2})$/.exec(mmYY);
  if (!match) {
    return false;
  }
  const month = Number(match[1]);
  const year = 2000 + Number(match[2]);
  if (month < 1 || month > 12) {
    return false;
  }
  const expiryDate = new Date(year, month, 0, 23, 59, 59);
  return expiryDate.getTime() >= Date.now();
}

export function isValidCvv(cvv) {
  return /^\d{3,4}$/.test(String(cvv));
}

export function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(email));
}
