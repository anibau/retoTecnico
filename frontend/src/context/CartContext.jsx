import { createContext, useCallback, useContext, useMemo, useState } from 'react';

const CartContext = createContext(null);

const ticketProductId = (premiereId) => `ticket-${premiereId}`;

export function CartProvider({ children }) {
  const [items, setItems] = useState([]);

  const addItem = useCallback((product) => {
    setItems((prev) => {
      const existing = prev.find((item) => item.productId === product.id);
      if (existing) {
        return prev.map((item) =>
          item.productId === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [
        ...prev,
        {
          productId: product.id,
          name: product.name,
          unitPrice: product.price,
          quantity: 1,
          kind: 'product',
        },
      ];
    });
  }, []);

  const setTicketQuantity = useCallback((premiere, quantity, unitPrice) => {
    setItems((prev) => {
      // Solo se admite una película activa por orden: cualquier selección de
      // entradas anterior se reemplaza por la nueva.
      const withoutTickets = prev.filter((item) => item.kind !== 'ticket');
      if (quantity <= 0) {
        return withoutTickets;
      }
      return [
        ...withoutTickets,
        {
          productId: ticketProductId(premiere.id),
          name: `Entrada: ${premiere.title}`,
          unitPrice,
          quantity,
          kind: 'ticket',
          premiereId: premiere.id,
        },
      ];
    });
  }, []);

  const updateQuantity = useCallback((productId, quantity) => {
    setItems((prev) => {
      if (quantity <= 0) {
        return prev.filter((item) => item.productId !== productId);
      }
      return prev.map((item) => (item.productId === productId ? { ...item, quantity } : item));
    });
  }, []);

  const removeItem = useCallback((productId) => {
    setItems((prev) => prev.filter((item) => item.productId !== productId));
  }, []);

  const clear = useCallback(() => setItems([]), []);

  const total = useMemo(
    () => items.reduce((sum, item) => sum + item.unitPrice * item.quantity, 0),
    [items]
  );

  const ticketItems = useMemo(() => items.filter((item) => item.kind === 'ticket'), [items]);
  const productItems = useMemo(() => items.filter((item) => item.kind !== 'ticket'), [items]);

  return (
    <CartContext.Provider
      value={{
        items,
        productItems,
        ticketItems,
        addItem,
        setTicketQuantity,
        updateQuantity,
        removeItem,
        clear,
        total,
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  const ctx = useContext(CartContext);
  if (!ctx) {
    throw new Error('useCart debe usarse dentro de CartProvider');
  }
  return ctx;
}
