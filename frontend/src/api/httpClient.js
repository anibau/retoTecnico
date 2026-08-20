import axios from 'axios';

const TOKEN_STORAGE_KEY = 'cines.session.token';

// Petición directa (sin pasar por createHttpClient) para evitar recursión con el
// propio interceptor de autenticación al pedir una sesión de reemplazo.
function requestFreshGuestToken() {
  const premieresBaseUrl = import.meta.env.VITE_PREMIERES_API_URL;
  return axios
    .post(`${premieresBaseUrl}/api/v1/session`, { mode: 'GUEST' })
    .then(({ data }) => {
      sessionStorage.setItem(TOKEN_STORAGE_KEY, data.token);
      return data.token;
    });
}

export function createHttpClient(baseURL) {
  const client = axios.create({ baseURL });

  client.interceptors.request.use((config) => {
    const token = sessionStorage.getItem(TOKEN_STORAGE_KEY);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  });

  // El JWT de invitado expira a los 30 min (o si el usuario entra directo a una
  // pantalla sin pasar por Home/Login primero, puede no haber token todavía).
  // Ante un 401/403 pedimos una sesión nueva y reintentamos una sola vez.
  client.interceptors.response.use(
    (response) => response,
    async (error) => {
      const { config, response } = error;
      const isAuthError = response && (response.status === 401 || response.status === 403);
      if (isAuthError && config && !config._retriedAfterAuthRefresh) {
        config._retriedAfterAuthRefresh = true;
        try {
          const freshToken = await requestFreshGuestToken();
          config.headers = config.headers || {};
          config.headers.Authorization = `Bearer ${freshToken}`;
          return client.request(config);
        } catch (refreshError) {
          sessionStorage.removeItem(TOKEN_STORAGE_KEY);
        }
      }
      return Promise.reject(error);
    },
  );

  return client;
}

export const TOKEN_KEY = TOKEN_STORAGE_KEY;
