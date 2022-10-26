import React, {useState, useEffect} from 'react';
import styled from 'styled-components';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/auth';
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css';
import { useRecipe } from '../context/recipe';

function Cuisine() {

  const [cuisine, setCuisine] = useState([]);
  const { recipes, getRecipes } = useRecipe()

  let params = useParams();
  const { user } = useAuth();
  const navigate = useNavigate();

  const errorPopup = () => {
    confirmAlert({
      message: 'Could not add recipe to database - you might have added it already',
      buttons: [
        {
          label: 'OK',
          onClick: () => {}
        },
      ]
    });
  };


  const getCuisine = async (name) => {
        const data = await fetch(`https://api.spoonacular.com/recipes/complexSearch?apiKey=2855122162134751b0137b0f335d3ac2&cuisine=${name}`);
        const spoonacularRecipes = await data.json();
        setCuisine(spoonacularRecipes.results);
    };

    const getRecipeInformationAndAddToBackend = async (recipeId) => {
      try {
        const data = await fetch(`https://api.spoonacular.com/recipes/${recipeId}/information?apiKey=2855122162134751b0137b0f335d3ac2`);
        const recipeInformation = await data.json();
        const payload = {
          name: recipeInformation.title,
          readyInMinutes: recipeInformation.readyInMinutes,
          calories: recipeInformation.calories,
          portion: recipeInformation.servings,
          preparation: recipeInformation.instructions,
          image: recipeInformation.image,
          ingredients: recipeInformation.extendedIngredients.map(ingredient => ({
            name: ingredient.name,
            amount: ingredient.amount,
            unit: ingredient.unit,
          }))
        };

        const response = await fetch(`http://localhost:8099/api/v1/recipe/add`, {
          method: 'PUT',
          headers: { 'Authorization': `Bearer ${user.token}`, 'Content-Type': 'application/json' },
          body: JSON.stringify(payload),
        });
        const recipe = await response.json();
        if (response.ok) {
          getRecipes();
          navigate(`/recipe/${recipe.recipeId}`);
        }
      } catch (err) {
        errorPopup()
        console.log(err);
      }
    };

    useEffect(() => {
        getCuisine(params.type);
    }, [params.type]);

  return (
  <Grid>
        {cuisine.map((item) => {
            return(
                <Card key={item.id}>
                    <img src={item.image} alt=""/>
                      <h4>{item.title}</h4>
                  {user && <Button onClick={() => getRecipeInformationAndAddToBackend(item.id)}>Add to database</Button>}
                </Card>
            )
        })}
    </Grid>
  );
}

const Grid = styled.div`
display: grid;
grid-template-columns: repeat(auto-fit, minmax(20rem, 1fr));
grid-gap: 3rem;
`;
const Card = styled.div`
img {
    width: 100%;
    border-radius: 2rem;
}

a {
    text-decoration: none;
}

h4 {
    text-allign: center;
    padding: 1rem;
}
`;

const Button = styled.button`
padding: 1rem;
color: black;
background: white;
border: 2px solid pink;
margin-right: 2rem;
`;

export default Cuisine;