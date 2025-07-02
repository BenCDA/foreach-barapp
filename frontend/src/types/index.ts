export interface Category {
    id: number
    nom: string
  }
  
  export interface Cocktail {
    id: number
    nom: string
    imageUrl: string
  }
  
  export interface CocktailDetail extends Cocktail {
    description: string
    ingredients: { nom: string; quantite: string }[]
    prices: SizePrice[]          // on ajoute bien la liste des prix ici
  }
  
  export interface SizePrice {
    id: number
    libelle: 'S' | 'M' | 'L'
    prix: number
  }
  
  export interface CartItem {
    id: number
    quantity: number
    price: number
    cocktail: { id: number; nom: string }
    size: { id: number; libelle: string }
  }
  
  export interface OrderSummary {
    id: number
    statut: string
    date: string
  }
  
  export interface OrderDetail {
    id: number
    items: CartItem[]
    total: number
    statut: string
  }
  