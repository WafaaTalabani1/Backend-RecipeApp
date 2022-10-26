export const Wrapper = ({ children }) => (
  <div className='my-16 text-white'>{children}</div>
)

export const Card = ({ children }) => (
  <div className='min-h-[25rem] rounded-[2rem] overflow-hidden text-white relative'>
    {children}
  </div>
)

export const RecipeImage = (props) => (
  <img
    {...props}
    className='rounded-[2rem] absolute left-0 w-full h-full object-cover'
  />
)

export const Gradient = () => <div className='z-[3] absolute w-full h-full' />
// eslint-disable-next-line no-lone-blocks
{
  /* background: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0,0, 0.5)); */
}

export const RecipeTitle = ({ title }) => (
  <p className='absolute z-10 left-[50%] bottom-0 translate-x-[-50%] text-white w-full text-center font-semibold text-base h-[40%] flex justify-center items-center'>
    {title}
  </p>
)
