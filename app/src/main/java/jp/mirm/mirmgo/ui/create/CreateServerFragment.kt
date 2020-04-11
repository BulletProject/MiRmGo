package jp.mirm.mirmgo.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.mirm.mirmgo.R
import kotlinx.android.synthetic.main.fragment_create_server.view.*

class CreateServerFragment : Fragment() {

    private lateinit var presenter: CreateServerPresenter
    private lateinit var adapter: CreateServerViewPagerAdapter

    companion object {
        fun newInstance(): CreateServerFragment {
            return CreateServerFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_create_server, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CreateServerPresenter(this)
        view.createServerViewPager.adapter = CreateServerViewPagerAdapter(activity!!.supportFragmentManager).also {
            this.adapter = it
        }

        CreateServerPresenter.setPage(0)
    }

}