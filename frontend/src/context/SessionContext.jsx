import { createContext, useCallback, useContext, useState } from 'react';
import { createGuestSession } from '../api/premieresApi.js';
import { TOKEN_KEY } from '../api/httpClient.js';

const SessionContext = createContext(null);

export function SessionProvider({ children }) {
  const [token, setToken] = useState(() => sessionStorage.getItem(TOKEN_KEY));
  const [loading, setLoading] = useState(false);

  const ensureGuestSession = useCallback(async () => {
    const existing = sessionStorage.getItem(TOKEN_KEY);
    if (existing) {
      setToken(existing);
      return existing;
    }
    setLoading(true);
    try {
      const session = await createGuestSession();
      sessionStorage.setItem(TOKEN_KEY, session.token);
      setToken(session.token);
      return session.token;
    } finally {
      setLoading(false);
    }
  }, []);

  const clearSession = useCallback(() => {
    sessionStorage.removeItem(TOKEN_KEY);
    setToken(null);
  }, []);

  return (
    <SessionContext.Provider value={{ token, loading, ensureGuestSession, clearSession }}>
      {children}
    </SessionContext.Provider>
  );
}

export function useSession() {
  const ctx = useContext(SessionContext);
  if (!ctx) {
    throw new Error('useSession debe usarse dentro de SessionProvider');
  }
  return ctx;
}
