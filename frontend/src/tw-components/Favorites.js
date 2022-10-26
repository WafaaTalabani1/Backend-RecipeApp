import { useEffect } from 'react'
import { Splide, SplideSlide } from '@splidejs/react-splide'
import '@splidejs/splide/dist/css/splide.min.css'
// Splide= caroussel-component, SplideSlide= each individual image
import { Link } from 'react-router-dom'
import { useRecipe } from '../context/recipe'
import {
  Wrapper,
  Gradient,
  RecipeImage,
  Card,
  RecipeTitle,
} from './rcomponents'

function Favorites() {
  const { favorites, getFavorites } = useRecipe()

  // run the function  as soon as component gets mounted

  useEffect(() => {
    getFavorites()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])
  // [] - empty arrays - only run function when component gets mounted

  return (
    <div>
      <Wrapper>
        <h3>Favorite Food</h3>
        <Splide
          options={{
            perPage: 4,
            arrows: false,
            drag: 'free',
            gap: '3rem',
          }}>
          {favorites.map((recipe) => {
            return (
              <SplideSlide key={recipe.id}>
                <Card>
                  <Link to={'/recipe/' + recipe.id}>
                    <RecipeTitle title={recipe.title} />
                    <RecipeImage src={recipe.image} alt={recipe.title} />
                    <Gradient />
                  </Link>
                </Card>
              </SplideSlide>
            )
          })}
        </Splide>
      </Wrapper>
    </div>
  )
}

export default Favorites
