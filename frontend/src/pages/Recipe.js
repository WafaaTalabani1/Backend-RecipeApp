import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import { confirmAlert } from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css';

import React from 'react'
import { useAuth } from '../context/auth';
import { useRecipe } from '../context/recipe';

function Recipe() {

  let params = useParams();
  const [details, setDetails] = useState({});
  const { user } = useAuth();
  const navigate = useNavigate();
  const { recipes, getRecipes } = useRecipe()
  const [activeTab, setActiveTab] = useState("instructions");

  const submit = () => {
    confirmAlert({
      title: 'Delete recipe',
      message: 'Are you sure that you want to delete this recipe?',
      buttons: [
        {
          label: 'Yes',
          onClick: () => deleteRecipe()
        },
        {
          label: 'No',
          onClick: () => {}
        }
      ]
    });
  };

  const fetchDetails = async () => {
    try {
      fetch(`http://localhost:8099/api/v1/recipe/id?id=${params.name}`, { headers: user ? { 'Authorization': `Bearer ${user.token}` } : undefined })
          .then((response) => response.json())
          .then((json) =>
              setDetails(json)
          )
    } catch (err) {
      console.error(err)
    }
  };

  const deleteRecipe = () => {
    try {
      fetch(`http://localhost:8099/api/v1/recipe?id=${params.name}`, { method: 'DELETE', headers: { 'Authorization': `Bearer ${user.token}` } })
          .then(() => {
            getRecipes();
            navigate('/recipes');
          })
    } catch (err) {
      console.error(err)
    }
  }

  useEffect(() => {
    fetchDetails();
  }, [params.name]);
  return (
      <DetailWrapper>
        <h1 className="full-width">Recipe - The Food Addicts</h1>
        <h2 className="full-width">{details.name}</h2>
        <div className="image">
          <img width={300} src={details.image} alt=""/>
        </div>
        <Info>
          <Button className={activeTab === "instructions" ? "active" : ""} onClick={() => setActiveTab("instructions")}>Instructions
          </Button>
          <Button className={activeTab === "ingredients" ? "active" : ""} onClick={() => setActiveTab("ingredients")}>Ingredients
          </Button>
          {user && user.role === 'ADMIN' && <Button onClick={submit}>Delete</Button>}
          {activeTab === 'instructions' && (
              <div>
                <h3 dangerouslySetInnerHTML={{ __html: details.preparation }}></h3>
              </div>
          )}
          {activeTab === 'ingredients' && (
              <div>
                <ul>
                  {details.ingredients.map((ingredient) =>
                      <li key={ingredient.id}>{ingredient.amount} {ingredient.unit} {ingredient.name}</li>
                  )}
                </ul>
              </div>)}

        </Info>
      </DetailWrapper>
  );
}

const DetailWrapper = styled.div`
padding-right: 1rem;
margin-top: 1rem;
margin-bottom: 5rem;
display: grid;
grid-template-columns: 1fr 2fr;

@media(max-width: 850px) {
  .image {
    display: flex;
    justify-content: center;
     grid-column: 1 / span 2;
  }
  
   
  h3 li {
    font-size: 1rem;
  }
}

.full-width {
  grid-column: 1 / span 2;
}

.active {
    background: linear-gradient(35deg, #f27121, #e94057);
    color: black;
}

h1 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

h2 {
    margin-bottom: 2rem;
    font-size: 1.2rem;
    width: 100%;
    word-break: break-word;
}
li {
    font-size: 1.2rem;
    line-height: 2.5rem;
}
ul {
    margin-top: 2rem;
}

h3 {
    margin-bottom: 2rem;
}
li {
    font-size: 1.2rem;
    line-height: 2.5rem;
}
ul {
    margin-top: 2rem;
}
`;

const Button = styled.button`
padding: 1rem 2rem;
color: black;
background: white;
border: 2px solid pink;
margin-right: 2rem;
font-weight: 600;
`;

const Info = styled.div`
padding-left: 2rem;

@media(max-width: 850px) {
   grid-column: 1 / span 2;
   margin-top: 1rem;
}
`;


export default Recipe;