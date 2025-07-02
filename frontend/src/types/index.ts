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
  }
  
  export interface SizePrice {
    id: number
    libelle: 'S' | 'M' | 'L'
    prix: number
  }
  
  export interface CartItem {
    id: number
    nom: string
    taille: string
    prix: number
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
  