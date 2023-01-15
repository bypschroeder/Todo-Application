import React from 'react'

interface ErrorMessageProps {
  handleError: () => void
}

export const ErrorMessage: React.FC<ErrorMessageProps> = (props) => {
  return (
    <div className='errorMessage'>
      <h1 className='errorMessage-title'>Error</h1>
      <p className='errorMessage-paragraph'>Pls enter a title before submitting the Todo</p>
      <button onClick={props.handleError}>Close</button>
    </div>
  )
}

export default ErrorMessage;