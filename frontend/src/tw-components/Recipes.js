import { useEffect } from 'react'
import { Splide, SplideSlide } from '@splidejs/react-splide'
import '@splidejs/splide/dist/css/splide.min.css'
import { Link } from 'react-router-dom'
import { useRecipe } from '../context/recipe'
import {
  Wrapper,
  Gradient,
  RecipeImage,
  Card,
  RecipeTitle,
} from './rcomponents'

function Recipes() {
  const { recipes, getRecipes } = useRecipe()

  // run the function  as soon as component gets mounted
  // useEffect(() => {
  //    getRecipes();
  // }, [])
  // [] - empty arrays - only run function when component gets mounted

  return (
    <Wrapper className='my-16 w-full'>
      <h3>Recipes</h3>
      <Splide
        options={{
          perPage: 3,
          arrows: false,
          drag: 'free',
          gap: '3rem',
        }}>
        {recipes.map((recipe) => {
          return (
            <SplideSlide key={recipe.recipeId}>
              <Card>
                <Link to={'/recipe/' + recipe.recipeId}>
                  <RecipeTitle title={`${recipe.name} - ${recipe.readyInMinutes} min`} />
                  <RecipeImage src={recipe.image} alt={recipe.name} />
                  <Gradient />
                </Link>
              </Card>
            </SplideSlide>
          )
        })}
      </Splide>
    </Wrapper>
  )
}

export default Recipes
