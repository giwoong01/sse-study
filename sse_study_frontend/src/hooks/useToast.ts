import { toast } from "react-toastify";

export const useToast = () => {
    const showToast = (message: string) => {
        toast.info(message);
    };

    return { showToast };
};