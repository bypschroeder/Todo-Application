import React from 'react'

interface HeaderProps {
  toggleModal: () => void;
}

const Header: React.FC<HeaderProps> = (props) => {

  function handleOpen() {
    window.history.pushState({id: undefined}, "a", "/")
    props.toggleModal();
  }

  return (
    <div className='header'>
      <h1 className='header-title'>To-Do-Liste</h1>
      <button className='header-button' onClick={handleOpen}>Add new To-Do</button>
    </div>
  )
}

export default Header