import { useContext, createContext, useState, useMemo } from 'react'
import { useAuth } from './auth';

const RecipeContext = createContext()

export const RecipeProvider = ({ children }) => {
  //useState: a variable that holds information, when the data in the variable changes, the UI reacts to it and would remove it too
  // in useState you define what kind of data you have - []
  const { user } = useAuth();
  const [recipes, setRecipes] = useState([])

  //favorites = variable
  const [favorites, setFavorites] = useState([])
  //setfavorites = function that would allow you to modify the variable

  //veggie = variable
  const [veggie, setVeggie] = useState([])
  //setVeggie  = function that would allow you to modify the variable

  const [isLoading, setIsLoading] = useState(false)

  const [error, setError] = useState('')

  const getRecipes = (query) => {
    setIsLoading(true)
    try {
      fetch('http://localhost:8099/api/v1/recipe', { headers: user ? { 'Authorization': `Bearer ${user.token}` } : undefined })
        .then((response) => response.json())
        .then((json) =>
          setRecipes(json.filter((recipe) => !query || recipe.name.includes(query)))
        )
    } catch (err) {
      setError(err.message)
    } finally {
      setIsLoading(false)
    }
  }

  const getFavorites = () => {
    setIsLoading(true)
    // store fetch into local storage
    // check in Dev Tools - Application - Storage - Local Storage -
    // we don't want new data after every refresh of page - limited amount (API KEY)
    // if there is nothing in localStorage, we set it
    // if there is sthg. in localStorage we just set our state to it - don't fetch it again, we have it there saved
    try {
      const check = localStorage.getItem('favorites')
      // to get data from localStorage - pull it back ; parsing it back from String to Array
      // in localStorage, you can only safe Strings
      check
        ? setFavorites(JSON.parse(check))
        : fetch(
            `https://api.spoonacular.com/recipes/random?apiKey=85ca4e96a81f443091947430c6bdb525&number=10`
          )
            .then((response) => response.json())
            .then((data) => {
              // set the fetched data here:
              localStorage.setItem('favorites', JSON.stringify(data.recipes))
              // stringify -> making Array to JSON String and saving it that way
              // parse -> and when you are pulling the item back, you are parsing it back from JSON string to the array
              setFavorites(data.recipes)
              console.log(data.recipes)
            })
    } catch (err) {
      setError(err.message)
    } finally {
      setIsLoading(false)
    }
  }

  const value = useMemo(
    () => ({
      recipes,
      isLoading,
      error,
      getRecipes,
      getFavorites,
      setFavorites,
      favorites,
      veggie,
      setVeggie,
    }),
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [recipes, isLoading, favorites, setFavorites, error]
  )

  return (
    <RecipeContext.Provider value={value}>
      {isLoading ? <p>Loading ...</p> : children}
    </RecipeContext.Provider>
  )
}

export const useRecipe = () => useContext(RecipeContext)
