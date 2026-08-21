const ICONS_BY_CATEGORY = {
  Combos: '🍿',
  Snacks: '🌭',
  Bebidas: '🥤',
  Dulces: '🍫',
  Pochoclos: '🍿',
  Helados: '🍦',
};

export function categoryIcon(category) {
  return ICONS_BY_CATEGORY[category] || '🍬';
}
