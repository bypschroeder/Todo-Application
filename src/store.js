import create from "zustand/react";
import {devtools, persist} from 'zustand/middleware'

// Zum Ausprobieren von Zustand


// interface ErrorState {
//   error: boolean
//   setError: () => void
// }

const errorStore = (set) => ({
  error: false,
  setError: () => {
    set((state) => (!state))
  }
})

const useErrorStore = create(
  devtools(
    persist(errorStore, {
      name: "error"
    })
  )
)

export default useErrorStore;