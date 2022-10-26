import { useState } from 'react'
import { FaSearch } from 'react-icons/fa'
import { useNavigate } from 'react-router-dom'

function Search() {
  // to get data from the input; "" - default
  const [input, setInput] = useState('')
  const navigate = useNavigate()

  // loading another page for search
  const submitHandler = (e) => {
    // Cuisinie and Search stays intact
    e.preventDefault()
    navigate('/searched/' + input)
  }
  return (
    <form className='w-full h-full mx-auto' onSubmit={submitHandler}>
      <div className='w-full relative'>
        <FaSearch className='absolute top-[50%] left-0 translate-x-full -translate-y-1/2 ' />
        <input
          onChange={(e) => setInput(e.target.value)}
          type='text'
          value={input}
          className='border-none text-2xl text-black py-4 px-12 rounded-2xl outline-none w-full'
        />
      </div>
    </form>
  )
}

// const FormStyle = styled.form`
// margin: 0rem 20rem;
//     background: linear-gradient(35deg, #f27121, #e94057)
// `

export default Search
