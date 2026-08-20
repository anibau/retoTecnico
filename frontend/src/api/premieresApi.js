import { createHttpClient } from './httpClient.js';

const client = createHttpClient(import.meta.env.VITE_PREMIERES_API_URL);

export async function fetchPremieres() {
  const { data } = await client.get('/api/v1/premieres');
  return data;
}

export async function createGuestSession() {
  const { data } = await client.post('/api/v1/session', { mode: 'GUEST' });
  return data;
}
