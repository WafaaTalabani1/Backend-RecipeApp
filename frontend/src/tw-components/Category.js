import { GiNoodles } from 'react-icons/gi'
import { FaHamburger, FaFish } from 'react-icons/fa'
import { TbSoup } from 'react-icons/tb'
import { NavLink } from 'react-router-dom'

function Category() {
  return (
   <div className="flex justify-center my-8">
        <NavLink to={'/cuisine/French'} className="flex flex-col justify-center items-center border-r-50 mr-8 cursor-pointer scale-[0.8] h-12 w-12 border-3xl bg-gradient-to-r from[#f27121] to[#e94057]">
        <FaFish />
        <h4>French</h4>
      </NavLink>
      <NavLink to={'/cuisine/Thai'} className="flex flex-col justify-center items-center border-r-50 mr-8 cursor-pointer scale-[0.8] h-12 w-12 border-3xl bg-gradient-to-r from[#f27121] to[#e94057]">
        <GiNoodles />
        <h4>Thai</h4>
      </NavLink>
      <NavLink to={'/cuisine/African'} className="flex flex-col justify-center items-center border-r-50 mr-8 cursor-pointer scale-[0.8] h-12 w-12 border-3xl bg-gradient-to-r from[#f27121] to[#e94057]">
        <TbSoup />
        <h4>African</h4>
      </NavLink>
      <NavLink to={'/cuisine/American'} className="flex flex-col justify-center items-center border-r-50 mr-8 cursor-pointer scale-[0.8] h-12 w-12 border-3xl bg-gradient-to-r from[#f27121] to[#e94057]">
        <FaHamburger className="text-black active:text-black"/>
        <h4 className="text-black text-base active:text-black">American</h4>
      </NavLink>
    </div>
  )
  }
  
export default Category
