import { createHttpClient } from './httpClient.js';

const client = createHttpClient(import.meta.env.VITE_CANDYSTORE_API_URL);

export async function fetchCandystoreProducts() {
  const { data } = await client.get('/api/v1/candystore/products');
  return data;
}
