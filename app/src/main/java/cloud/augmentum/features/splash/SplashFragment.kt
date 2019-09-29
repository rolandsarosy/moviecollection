package cloud.augmentum.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cloud.augmentum.R
import cloud.augmentum.common.waitAndRun

class SplashFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()

        private const val DELAY_TIME = 1300L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()
        waitAndRun(DELAY_TIME) {
            findNavController().navigate(R.id.action_splashFragment_to_catalogFragment)
        }
    }
}