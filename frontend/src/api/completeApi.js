import { createHttpClient } from './httpClient.js';

const client = createHttpClient(import.meta.env.VITE_COMPLETE_API_URL);

export async function submitPayment(paymentRequest) {
  const { data } = await client.post('/api/v1/payments', paymentRequest);
  return data;
}

export async function completeOrder(completeRequest) {
  const { data } = await client.post('/api/v1/complete', completeRequest);
  return data;
}
